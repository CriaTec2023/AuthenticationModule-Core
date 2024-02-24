package com.developement.authentication.application.services.impl;

import com.developement.authentication.application.dtos.EmailDto;
import com.developement.authentication.application.dtos.ResponseDto;
import com.developement.authentication.application.services.IEmailService;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements IEmailService {
    @Override
    public ResponseDto sendEmail(EmailDto emailObjDto) {
        return null;
    }
}
