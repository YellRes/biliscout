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

@SpringBootTest
class BiliscoutApplicationTests {

	@Test
	void shouldFindTaskById() {
		ResearchTask task = researchTaskMapper.selectById(1L);

		assertNotNull(task);
		assertEquals(1L, task.getId());
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

		ResearchTask task = new ResearchTask();
		task.setQuestion("任务测试");
		task.setStatus(TaskStatus.QUEUED);
		task.setCreatedAt(LocalDateTime.now(ZoneOffset.UTC));

		int affectedRows = researchTaskMapper.insert(task);

		assertEquals(1, affectedRows);
		assertNotNull(task.getId());
	}

	@Test
	@Transactional
	void shouldUpdateTaskStatus() {
		ResearchTask task = new ResearchTask();
		task.setQuestion("更新状态测试");
		task.setStatus(TaskStatus.QUEUED);
		task.setCreatedAt(LocalDateTime.now(ZoneOffset.UTC));
		researchTaskMapper.insert(task);

		int affectedRows = researchTaskMapper.updateStatus(
			task.getId(), TaskStatus.DONE
		);

		ResearchTask updated = researchTaskMapper.selectById(task.getId());

		assertEquals(1, affectedRows);
		assertNotNull(updated);
		assertEquals(TaskStatus.DONE, updated.getStatus());
	}


	@Test
	@Transactional
	void shouldDeleteTask() {
		ResearchTask task = new ResearchTask();
		task.setQuestion("测试删除任务");
		task.setStatus(TaskStatus.QUEUED);
		task.setCreatedAt(LocalDateTime.now(ZoneOffset.UTC));
		researchTaskMapper.insert(task);

		int affectedRows = researchTaskMapper.deleteById(task.getId());

		ResearchTask deleted = researchTaskMapper.selectById(task.getId());

		assertEquals(1, affectedRows);
		assertNull(deleted);
	}
}
