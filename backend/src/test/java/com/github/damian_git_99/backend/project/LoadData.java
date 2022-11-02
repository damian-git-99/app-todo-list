package com.github.damian_git_99.backend.project;

import java.util.Date;
import java.util.List;

public class LoadData {

    public static List<Project> loadProjects(){
        Project project = Project.builder()
                .id(1L)
                .name("project 1")
                .createdAt(new Date())
                .description("my new Project 1")
                .build();

        Project project2 = Project.builder()
                .id(2L)
                .name("project 2")
                .createdAt(new Date())
                .description("my new Project 2")
                .build();

        Project project3 = Project.builder()
                .id(3L)
                .name("project 3")
                .createdAt(new Date())
                .description("my new Project 3")
                .build();

        List<Project> projects = List.of(project, project2, project3);
        return projects;
    }

    public static Project LoadProject(){
        return Project.builder()
                .id(1L)
                .name("project 1")
                .createdAt(new Date())
                .description("my new Project 1")
                .build();
    }

}
