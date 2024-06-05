package com.duskio.common;

import com.duskio.common.constant.AnsiColor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assert;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.description.Description;
import org.assertj.core.description.LazyTextDescription;
import org.assertj.core.description.TextDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

@Slf4j
public class SoftAssertJ2 extends SoftAssertions {

    private static final StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
    private static final ThreadLocal<AssertionDescription> messages = new ThreadLocal<>();
    private static final ThreadLocal<Logger> logger = new ThreadLocal<>();
    private static final ThreadLocal<Object> savedActual = new ThreadLocal<>();
    private static SoftAssertJ2 softAssertJ;

    public static SoftAssertJ2 getInstance() {
        if (softAssertJ == null) {
            softAssertJ = new SoftAssertJ2();
        }
        return softAssertJ;
    }

    private SoftAssertJ2() {
    }

    public SoftAssertJ2 as(String description, Object... args) {
        messages.set(AssertionDescription.builder().formatter(description).args(args).build());
        return this;
    }

    public SoftAssertJ2 as(Description description) {
        messages.set(AssertionDescription.builder().description(description).build());
        return this;
    }

    public SoftAssertJ2 as(Supplier<String> description) {
        messages.set(AssertionDescription.builder().supplier(description).build());
        return this;
    }

    @Override
    public <SELF extends Assert<? extends SELF, ? extends ACTUAL>, ACTUAL> SELF proxy(Class<SELF> assertClass,
                                                                                      Class<ACTUAL> actualClass,
                                                                                      ACTUAL actual) {
        SELF proxy = super.proxy(assertClass, actualClass, actual);
        savedActual.set(actual);

        try {
            STACK_WALKER.walk(s -> s.skip(2).findFirst())
                        .ifPresent(stackFrame -> logger.set(LoggerFactory.getLogger(stackFrame.getClassName())));
        } catch (Exception ignore) {
        }

        if (messages.get() != null) {
            AssertionDescription description = messages.get();
            if (description.getFormatter() != null) {
                proxy.as(description.getFormatter(), description.getArgs());
            } else if (description.getDescription() != null) {
                proxy.as(description.getDescription());
            } else if (description.getSupplier() != null) {
                proxy.as(description.getSupplier());
            }
        }
        return proxy;
    }

    @Override
    public void succeeded() {
        String message = "";
        if (messages.get() != null) {
            AssertionDescription description = messages.get();
            if (description.getFormatter() != null) {
                message = new TextDescription(description.getFormatter(), description.getArgs()).value();
            } else if (description.getDescription() != null) {
                message = description.getDescription().value();
            } else if (description.getSupplier() != null) {
                message = new LazyTextDescription(description.getSupplier()).value();
            }
            messages.remove();
        }

        if (savedActual.get() != null) {
            logPass("[" + message + "] actual: " + savedActual.get().toString());
            savedActual.remove();
        }
    }

    @Override
    public void onAssertionErrorCollected(AssertionError e) {
        messages.remove();
        savedActual.remove();
        logFail(e.getMessage());
    }

    private void logPass(String message) {
        message = message.trim()
                         .replaceAll("[\\s]+", " ");
        getLogger().info("   " + AnsiColor.GREEN_BOLD + "PASS" + AnsiColor.RESET + "   " + message);
    }

    private void logFail(String message) {
        message = message.trim()
                         .replaceAll("[\\s]+", " ")
                         .replace("Expecting", "expected");
        getLogger().info("   " + AnsiColor.RED_BOLD + "FAIL" + AnsiColor.RESET + "   " + message);
    }

    private Logger getLogger() {
        if (logger.get() != null) {
            return logger.get();
        } else {
            return log;
        }
    }

    @Builder
    @Getter
    static class AssertionDescription {
        private String formatter;
        private Object[] args;
        private Description description;
        private Supplier<String> supplier;
    }
}
