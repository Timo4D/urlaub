package hs.aalen.urlaub.blackbox;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import hs.aalen.urlaub.vacation.Vacation;
import hs.aalen.urlaub.vacation.VacationController;
import hs.aalen.urlaub.vacation.VacationService;
import jakarta.transaction.Transactional;


@SpringBootTest
@Transactional
@Rollback
public class BlackboxTestsEquivalence {


  @Autowired
  private VacationController vacationController = new VacationController();
    @Autowired
  private VacationService vacationService = new VacationService();







    
    
}
