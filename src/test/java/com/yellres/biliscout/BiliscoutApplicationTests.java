package com.yellres.biliscout;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import com.yellres.biliscout.mapper.ResearchTaskMapper;
import com.yellres.biliscout.entity.ResearchTask;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.yellres.biliscout.entity.TaskStatus;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.transaction.annotation.Transactional;
import com.yellres.biliscout.entity.ResearchTaskInsert;

@SpringBootTest
class BiliscoutApplicationTests {

	@Test
	void shouldFindTaskById() {
		ResearchTask task = researchTaskMapper.selectById(1L);

		assertNotNull(task);
		assertEquals(1L, task.id());
	}

	@Test
	void shouldReturnNullWhenTaskMissing() {
		ResearchTask task = researchTaskMapper.selectById(999L);

		assertNull(task);
	}

	@Autowired
	private ResearchTaskMapper researchTaskMapper;


	@Test
	@Transactional
	void shouldInsertTask() {
		// ResearchTask task = new ResearchTask(
		// 	null,
		// 	TaskStatus.QUEUED,
		// 	LocalDateTime.now(ZoneOffset.UTC),
		// 	"新增任务测试"
		// );

		ResearchTaskInsert task = new ResearchTaskInsert();
		task.setQuestion("任务测试");
		task.setStatus(TaskStatus.QUEUED);
		task.setCreatedAt(LocalDateTime.now(ZoneOffset.UTC));

		int affectedRows = researchTaskMapper.insert(task);

		assertEquals(1, affectedRows);
		assertNotNull(task.getId());
	}

}
