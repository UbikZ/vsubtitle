package org.ubikz.vsubtitle.console;

import org.flywaydb.core.Flyway;

import java.sql.DriverManager;

class Application {
    private static final String CLASS_PGSQL_DRIVE = "org.postgresql.Driver";
    private static final String ARG_DB_MIGRATE = "migrate";
    private static final String ARG_DB_CHECK = "check";


    public static void main(String[] args) throws Exception {
        if (args.length != 4) {
            throw new Exception("Wrong number of parameters.");
        }

        String mode = args[0];
        String connexionString = args[1];
        String username = args[2];
        String password = args[3];

        switch (mode) {
            case ARG_DB_CHECK:
                checkDatabase(connexionString, username, password);
                break;
            case ARG_DB_MIGRATE:
                migrate(connexionString, username, password);
                break;
            default:
                throw new Exception("Wrong mode provided.");
        }
    }

    private static void migrate(String connexionString, String username, String password) throws Exception {
        Flyway flyway = new Flyway();
        flyway.setDataSource(connexionString, username, password);
        flyway.migrate();
    }

    private static void checkDatabase(String connexionString, String username, String password) throws Exception {
        int maxTries = 60;

        while (true) {
            try {
                Class.forName(CLASS_PGSQL_DRIVE);
                DriverManager.getConnection(connexionString, username, password);
                break;
            } catch (Exception e) {
                --maxTries;
                Thread.sleep(1000);
                if (maxTries == 0) {
                    System.err.println(e.getClass().getName() + " : Max tried connection reached. There is an issue.");
                    System.err.println(e.getMessage());
                    System.exit(0);
                }
            }
        }

        System.out.println("Opened database successfully");
    }
}