package com.example.demo.service;

        import com.example.demo.exception.ServiceException;
        import com.example.demo.model.req.RegisterReq;
        import com.example.demo.model.req.SignInReq;
        import com.example.demo.model.vo.UserVO;
        import org.springframework.stereotype.Service;


public interface NaturalPersonService {


    UserVO register(RegisterReq registerReq) throws ServiceException;

}
