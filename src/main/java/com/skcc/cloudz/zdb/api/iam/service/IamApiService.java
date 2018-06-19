package com.skcc.cloudz.zdb.api.iam.service;

import com.skcc.cloudz.zdb.api.iam.domain.vo.ApiResponseVo;

public interface IamApiService {
    
    
    ApiResponseVo logout(String userId);
    

}
