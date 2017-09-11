import com.guorenbao.taskmanager.Application;
import com.guorenbao.taskmanager.client.TaskManagerClient;
import com.guorenbao.taskmanager.domain.entity.TaskManagerList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = Application.class)
public class FeignCallTest {

  @Autowired
  TaskManagerClient taskManagerClient;

  @Test
  public void test() {
    System.out.println("111111111111111111111111111111111111111111111111111");
    taskManagerClient.exec(new TaskManagerList());
  }


}
