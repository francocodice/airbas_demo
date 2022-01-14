package com.afm.profileservice.service;


import com.afm.profileservice.repository.UserBasDetailRepository;
import lombok.RequiredArgsConstructor;
import model.auth.UserBasDetail;
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
        return userBasDetailRepository.findByEmail(email);
        //return userBasDetailRepository.findByUserbas(userbas);
    }


    public void createDetailsUser(UserPayload payload){
        //UserBasDetail userBasDetail = userBasDetailRepository.findByEmail(payload.getEmail());
        UserBasDetail userBasDetail = new UserBasDetail();

        userBasDetail.setBirthdate(payload.getBirthdate());
        userBasDetail.setCreditcard(payload.getCreditcard());
        userBasDetail.setFirstname(payload.getFirstname());
        userBasDetail.setSecondname(payload.getSecondname());
        userBasDetail.setTelephone(payload.getTelephone());
        userBasDetail.setEmail(payload.getEmail());
        //userBasDetail.setUserbas(refUser);

        //refUser.setUserbasdetail(userBasDetail);
        //userBasRepository.save(ref);
        userBasDetailRepository.save(userBasDetail);
    }

    public boolean updateDetailsUser(UserPayload payload){
        UserBasDetail userBasDetail = userBasDetailRepository.findByEmail(payload.getEmail());

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
            userBasDetail.setFirstname(payload.getSecondname());
        }

        userBasDetailRepository.save(userBasDetail);
        return true;
    }

}
