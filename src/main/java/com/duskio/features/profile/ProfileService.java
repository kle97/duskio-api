package com.duskio.features.profile;

import com.duskio.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j @RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Transactional(readOnly = true)
    public Profile findById(Long id) {
        return profileRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Profile.class, id));
    }

    @Transactional(readOnly = true)
    public ProfileEntityResponse findEntityById(Long id) {
        Profile entity = profileRepository.findEntityById(id).orElseThrow(() -> new ResourceNotFoundException(Profile.class, id));
        return profileMapper.toProfileEntityResponse(entity);
    }

    @Transactional(readOnly = true)
    public Page<ProfileResponse> findAll(Pageable pageable) {
        return profileRepository.findAll(pageable).map(profileMapper::toProfileResponse);
    }

    @Transactional
    public ProfileResponse save(ProfileResponse profileRequest) {
        Profile transientProfile = profileMapper.toProfile(profileRequest);
        return profileMapper.toProfileResponse(profileRepository.save(transientProfile));
    }

    @Transactional
    public ProfileResponse update(Long id, ProfileRequest profileRequest) {
        Profile currentProfile = findById(id);
        return profileMapper.toProfileResponse(profileMapper.toExistingProfile(profileRequest, currentProfile));
    }

    @Transactional
    public void delete(Long id) {
        if (profileRepository.existsById(id)) {
            profileRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(Profile.class, id);
        }
    }
}
