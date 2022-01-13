package com.afm.authservice.service;



import com.afm.authservice.repository.UserBasDetailRepository;
import lombok.RequiredArgsConstructor;

import model.auth.UserBas;
import model.auth.UserBasDetail;
import model.utils.UserPayload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserBasDetailsService {
    private final UserBasDetailRepository userBasDetailRepository;

    public UserBasDetail findDetails(UserBas userbas){
        return userBasDetailRepository.findByUserbas(userbas);
    }


    public void createDetailsUser(UserPayload payload){
        UserBasDetail userBasDetail = userBasDetailRepository.findByUserbasId(payload.getId());

        userBasDetail.setBirthdate(payload.getBirthdate());
        userBasDetail.setCreditcard(payload.getCreditcard());
        userBasDetail.setFirstname(payload.getFirstname());
        userBasDetail.setSecondname(payload.getSecondname());
        userBasDetail.setTelephone(payload.getTelephone());
        //userBasDetail.setUserbas(refUser);

        //refUser.setUserbasdetail(userBasDetail);
        //userBasRepository.save(ref);
        userBasDetailRepository.save(userBasDetail);
    }

    public boolean updateDetailsUser(UserPayload payload){
        UserBasDetail userBasDetail = userBasDetailRepository.findByUserbasId(payload.getId());

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
