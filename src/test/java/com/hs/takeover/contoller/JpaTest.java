package com.hs.takeover.contoller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.hs.gw.common.DeptID;
import com.hs.gw.common.UserID;
import com.hs.gw.org.info.User;
import com.hs.takeover.domain.TakeOver;
import com.hs.takeover.repository.TakeOverRepository;


@SpringBootTest
public class JpaTest {

	@Autowired
    private TakeOverRepository takeOverRepository;

    @Test
    @Transactional
    void testJpa() {        
    	TakeOver takeOver = new TakeOver();
    	//인계자
    	User handOverUser = sampleHandOverUser();
    	takeOver.setHandOverUserId(handOverUser.getID().getID());
    	takeOver.setHandOverUserName(handOverUser.getName());
    	takeOver.setHandOverDeptId(handOverUser.getDeptID().getID());
    	takeOver.setHandOverDeptName(handOverUser.getDeptName());
    	//인수자
    	User takeOverUser = sampleTakeOverUser();
    	takeOver.setTakeOverUserId(takeOverUser.getID().getID());
    	takeOver.setTakeOverUserName(takeOverUser.getName());
    	takeOver.setTakeOverDeptId(takeOverUser.getDeptID().getID());
    	takeOver.setTakeOverDeptName(takeOverUser.getDeptName());
    	//입회자
    	User takeObserveUser = sampleObserveUser();
    	takeOver.setObserveUserId(takeObserveUser.getID().getID());
    	takeOver.setObserveUserName(takeObserveUser.getName());
    	takeOver.setObserveDeptId(takeObserveUser.getDeptID().getID());
    	takeOver.setObserveDeptName(takeObserveUser.getDeptName());
    	
    	takeOverRepository.save(takeOver);
    }
    
    private User sampleHandOverUser() { 
    	User user = new User();
    	user.setID(new UserID("000000001"));
    	user.setName("admin");
    	user.setDeptID(new DeptID("000000101"));
    	user.setDeptName("system");
    	return user;
    }
    
    private User sampleTakeOverUser() { 
    	User user = new User();
    	user.setID(new UserID("001010717"));
    	user.setName("홍길동");
    	user.setDeptID(new DeptID("000011613"));
    	user.setDeptName("컴포넌트팀(new)_11");
    	return user;
    }
    
    private User sampleObserveUser() { 
    	User user = new User();
    	user.setID(new UserID("001027000"));
    	user.setName("김문기4");
    	user.setDeptID(new DeptID("000011328"));
    	user.setDeptName("지식정보실");
    	return user;
    }
}
