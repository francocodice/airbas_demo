package com.afm.profileservice.service;


import com.afm.profileservice.repository.UserBasDetailRepository;
import lombok.RequiredArgsConstructor;
import model.profile.UserBasDetail;
import model.exception.BadRequestException;
import model.exception.ResourceNotFoundException;
import model.utils.UserPayload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserBasDetailsService {
    private final UserBasDetailRepository userBasDetailRepository;

    public List<UserBasDetail> findAll(){
        return userBasDetailRepository.findAll();
    }

    public UserBasDetail findDetails(String email){
        UserBasDetail userBasDetail = userBasDetailRepository.findByEmail(email);
        if (userBasDetail == null)
            throw new ResourceNotFoundException("Detail not found");
        return userBasDetail;
    }


    public UserBasDetail createDetailsUser(UserPayload payload){
        UserBasDetail userBasDetail = userBasDetailRepository.findByEmail(payload.getEmail());
        if (userBasDetail != null)
            throw new BadRequestException("Detail user already exsit");

        userBasDetail = new UserBasDetail();
        userBasDetail.setBirthdate(payload.getBirthdate());
        userBasDetail.setCreditcard(payload.getCreditcard());
        userBasDetail.setFirstname(payload.getFirstname());
        userBasDetail.setSecondname(payload.getSecondname());
        userBasDetail.setTelephone(payload.getTelephone());
        userBasDetail.setEmail(payload.getEmail());
        userBasDetailRepository.save(userBasDetail);
        return userBasDetail;
    }

    public UserBasDetail updateDetailsUser(UserPayload payload){
        UserBasDetail userBasDetail = userBasDetailRepository.findByEmail(payload.getEmail());
        if (userBasDetail == null)
            throw new ResourceNotFoundException("Email not exists");

        if(!payload.getTelephone().isBlank()){
            userBasDetail.setTelephone(payload.getTelephone());
        }
        if(!payload.getCreditcard().isBlank()){
            userBasDetail.setCreditcard(payload.getCreditcard());
        }
        if(payload.getBirthdate() != null){
            userBasDetail.setBirthdate(payload.getBirthdate());
        }
        if(!payload.getFirstname().isBlank()){
            userBasDetail.setFirstname(payload.getFirstname());
        }
        if(!payload.getSecondname().isBlank()){
            userBasDetail.setSecondname(payload.getSecondname());
        }
        userBasDetailRepository.save(userBasDetail);
        return userBasDetail;
    }

}
