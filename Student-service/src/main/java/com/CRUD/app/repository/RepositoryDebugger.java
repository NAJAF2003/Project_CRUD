package com.CRUD.app.repository;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RepositoryDebugger {

    @EventListener
    public void handleRepositoryEvents(Object event){

        log.debug("Repository Operations Occured" + event.getClass().getSimpleName());
    }
}
