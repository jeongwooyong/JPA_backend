package org.zerock.apiserver.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.apiserver.domain.Todo;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@Slf4j
public class TodoRepositoryTests {
    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void test1(){
        Assertions.assertNotNull(todoRepository);
        log.info(todoRepository.getClass().getName());
    }

    @Test
    public void insert(){
        for(int i=0; i<100; i++) {
            Todo todo = Todo.builder()
                    .title("Title.." + i)
                    .content("Content...")
                    .dueDate(LocalDate.of(2023, 12, 30))
                    .build();

            Todo result = todoRepository.save(todo);

            log.info(String.valueOf(result));
        }
    }
    @Test
    public void testRead(){
        Long tno = 1L;
        Optional<Todo> result = todoRepository.findById(tno);
        Todo todo = result.orElseThrow();

        log.info(String.valueOf(result));
    }
    @Test
    public void testUpdate(){
        //먼저 로딩하고, 객체를 변경한다. /setter 같은것을 사용하긴 하는데, 다른용어를 많이 쓴다.
        Long tno = 1L;
        Optional<Todo> result = todoRepository.findById(tno);
        Todo todo = result.orElseThrow();

        todo.changeTitle("Update TItle");
        todo.changeContent("Update content");
        todo.changeComplete(true);

        Todo save = todoRepository.save(todo);

        log.info(String.valueOf(save));
    }
    @Test
    public void testPaging(){
        //페이지 번호는 0부터 시작한다.
        Pageable pageable = PageRequest.of(0,10, Sort.by("tno").descending());

        Page<Todo> result = todoRepository.findAll(pageable);

        log.info(result.toString());
        log.info(result.getContent().toString());

    }

//    @Test
//    public void testSearch1(){
//        todoRepository.search1();
//    }
}
