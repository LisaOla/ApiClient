package com.apiclient;

import java.util.Scanner;

public class MyProgram {
    private ApiClient myApiClient;

    // Vår konstruktor som skapar ett nytt ApiClient-objekt
    public MyProgram() {
        myApiClient = new ApiClient("http://127.0.0.1:8080/api/v1");
    }

    // Där vårt program startar
    public void start() {
        boolean programRunning = true;

        while (programRunning) {
            System.out.println();
            System.out.println("=========================================");
            System.out.println("What would you like to do?");
            System.out.println("1. Add a job to the list");
            System.out.println("2. Get list of Jobs");
            System.out.println("3. Get a specific job");
            System.out.println("4. update a job");
            System.out.println("5. delete a job");
            System.out.println("6. Clear list of jobs");
            System.out.println("7. Exit program");
            System.out.println("=========================================");
            System.out.println();

            int userChoice = getUserInt();


            switch (userChoice) {
                case 1:
                    addJobs();
                    break;
                case 2:
                    readListOfJobs();
                    break;
                case 3:
                    readJobById();
                    break;
                case 4:
                    updateJob();
                    break;
                case 5:
                    deleteJobById();
                    break;
                case 6:
                    clearListOfJobs();
                    break;

                case 7:
                    System.out.println("Goodbye.");
                    programRunning = false;
            }

        }
    }


    //CRUD - Create
    public void addJobs() {
        System.out.println("What's the jobs called?");
        String title = getUserString();

        System.out.println("What is the salary?");
        int basesalary = getUserInt();

        Job newJob = new Job(title, basesalary);
        boolean success = myApiClient.addJob(newJob);

        if (success) {
            System.out.println("Job added!");
        } else {
            System.out.println("Issue adding job.");
        }

    }

    //CRUD - Read
    public void readListOfJobs() {
        Job[] jobs = myApiClient.getJobs();

        System.out.println("Jobs");
        System.out.println("-----------------------------------------");

        if (jobs.length > 0) {
            for (int i = 0; i < jobs.length; i++) {
                int id = jobs[i].id;
                String title = jobs[i].title;
                int basesalary = jobs[i].basesalary;

                System.out.printf("-> %d %s %d SEK\n", id, title, basesalary);
            }
        } else {
            System.out.println("No jobs on the list ");
        }
    }

    //CRUD - Read a specific job by Id
    public void readJobById() {
        System.out.println("What is the job Id you want to see?");
        int jobid = getUserInt();
        Job jobObject = myApiClient.readJobById(jobid);

        if(jobObject.title != null){
            System.out.println("-----------------------------------------");

            System.out.println("Details of Job Id [" + jobid + "] is: " + jobObject.title +" " + jobObject.basesalary + " SEK");
        } else {
            System.out.printf("There is no job with id %d in the list", jobid);
        }

    }

    //CRUD - Update
    public void updateJob() {
        System.out.println("What is the job Id to update?");
        int jobid = getUserInt();
        Job jobObject = myApiClient.readJobById(jobid);
        if(jobObject.title != null){
            System.out.println("What's the new job title?");
            String title = getUserString();
            System.out.println("What is the new base salary?");
            int basesalary = getUserInt();

            Job jobChanges = new Job(title, basesalary);
            boolean success = myApiClient.updateJob(jobid, jobChanges);

            if (success) {
                System.out.println("Job updated!");
            } else {
                System.out.println("Issue updating job.");
            }
        } else {
            System.out.printf("There is no job with id %d in the list", jobid);
        }

    }


    // CRUD - Delete blog by Id
    public void deleteJobById() {
        System.out.println("What is the job Id to delete?");
        int jobid = getUserInt();

        Job jobObject = myApiClient.readJobById(jobid);

        if(jobObject.title != null){
            if (myApiClient.deleteJobById(jobid)) {
                System.out.println("Job is deleted!");
            } else {
                System.out.println("Issue deleting the job");
            }
        } else {
            System.out.printf("There is no job with id %d in the list", jobid);
        }


    }
    //CRUD - Delete all the list
    public void clearListOfJobs() {
        if (myApiClient.clearJobs()) {
            System.out.println("List of jobs cleared!");
        } else {
            System.out.println("Issue clearing list of jobs.");
        }
    }


    public String getUserString() {
        Scanner myScanner = new Scanner(System.in);

        String myString;

        while (true) {
            try {
                System.out.print("> ");
                myString = myScanner.nextLine();
                break;
            } catch (Exception e) {
                //System.out.println("Exception: " + e);
                System.out.println("Felaktig indata");
            }
        }

        return myString;
    }

    public int getUserInt() {
        Scanner myScanner = new Scanner(System.in);

        int myInteger;

        while (true) {
            try {
                System.out.print("> ");
                myInteger = Integer.parseInt(myScanner.nextLine());
                break;
            } catch (Exception e) {
                //System.out.println("Exception: " + e);
                System.out.println("Felaktig indata");
            }
        }

        return myInteger;
    }
}
