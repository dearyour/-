package com.seonbi.api.response;

import com.seonbi.api.model.MemberDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MemberGetAllRes extends BaseResponseBody{

    private List<MemberDto> members;

    public static MemberGetAllRes of(Integer statusCode, String message, List<MemberDto> members) {
        MemberGetAllRes res = new MemberGetAllRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setMembers(members);

        return res;
    }

}
