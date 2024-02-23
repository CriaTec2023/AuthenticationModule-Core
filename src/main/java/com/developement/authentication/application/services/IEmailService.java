package com.developement.authentication.application.services;


import com.developement.authentication.application.dtos.EmailDto;
import com.developement.authentication.application.dtos.ResponseDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


public interface IEmailService {

    @RequestMapping(value = "/sending-email", method = RequestMethod.POST)
    ResponseDto sendEmail(EmailDto emailObjDto);
}
