package com.skcc.cloudz.zdb.api.iam.service;

import com.skcc.cloudz.zdb.api.iam.domain.vo.ApiResponseVo;
import com.skcc.cloudz.zdb.api.iam.domain.vo.ZcpUserResVo;

public interface IamApiService {
    
	ZcpUserResVo getUser(String userId);
	
    ApiResponseVo logout(String userId);
    

}
