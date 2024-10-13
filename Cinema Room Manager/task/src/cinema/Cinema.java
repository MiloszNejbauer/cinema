package cinema;

import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seats = scanner.nextInt();

        char[][] cinema = new char[rows][seats];
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < seats; j++){
                cinema[i][j] = 'S';
            }
        }
        int purchasedTickets = 0;

        printCinema(cinema);

        boolean continueProgram = true;

        while (continueProgram) {
            continueProgram = menu(cinema, rows, seats, scanner, purchasedTickets); // Uaktualniaj flagę na podstawie opcji z menu
        }
        scanner.close();

    }
    private static void printCinema(char[][] cinema){
        System.out.println("Cinema: ");

        System.out.print("  ");

        for(int x = 1; x <= cinema[0].length; x++){
            System.out.print(x + " ");
        }
        System.out.println(" ");
        for(int i = 0; i < cinema.length; i++){
            System.out.print((i + 1) + " ");
            for(int j = 0; j < cinema[i].length; j++){
                System.out.print(cinema[i][j] + " ");
            }
            System.out.println();
        }

    }
    private static boolean menu(char[][]cinema, int rows, int seats, Scanner scanner, int purchasedTickets){

        System.out.println("""
                
                1.Show the seats
                2.Buy a ticket
                3.Statistics
                0.Exit
                """);
        int menuOption;
        menuOption = scanner.nextInt();
        switch(menuOption){
            case 1:
                printCinema(cinema);
                break;
            case 2:
                buyTicket(cinema, rows, seats, scanner);
                break;
            case 3:
                printStatistics(rows, seats, cinema, purchasedTickets);
                break;
            case 0:
                return false;
            default:
                System.out.println("Wrong menu option");
                break;
        }
        return true;
    }
    private static void buyTicket(char[][] cinema, int rows, int seats, Scanner scanner) {
        int reservedRow;
        int reservedSeat;

        // Pętla dopóki użytkownik nie poda poprawnych danych
        while (true) {
            System.out.println("Enter a row number: ");
            reservedRow = scanner.nextInt();
            System.out.println("Enter a seat number in that row: ");
            reservedSeat = scanner.nextInt();
            
            if (reservedRow > 0 && reservedRow <= rows && reservedSeat > 0 && reservedSeat <= seats) {
                if (cinema[reservedRow - 1][reservedSeat - 1] == 'B') {
                    System.out.println("That ticket has already been purchased.");
                } else {
                    int price = (rows * seats <= 60 || reservedRow <= rows / 2) ? 10 : 8;
                    System.out.println("Ticket price: $" + price);
                    cinema[reservedRow - 1][reservedSeat - 1] = 'B';
                    break;
                }
            } else {
                System.out.println("Wrong input!");
            }
        }
    }

    private static void printStatistics(int rows, int seats, char[][] cinema, int purchasedTickets){
        int totalSeats = rows * seats;
        int totalIncome;
        if(totalSeats <= 60){
            totalIncome = totalSeats * 10;
        }else if(rows % 2 == 0){
            totalIncome = ((rows / 2) * seats * 10) + ((rows / 2) * seats * 8);
        }else{
            totalIncome = ((rows / 2) * seats * 10) + (((rows / 2) + 1) * seats * 8);
        }

        calculateTickets(cinema, purchasedTickets, rows, seats);
        System.out.println("Total seats: " + totalSeats);
        System.out.println("Total income : $" + totalIncome);
    }

    public static void calculateTickets(char[][] cinema, int purchasedTickets, int rows, int seats){
        int t = purchasedTickets;
        int currentIncome = 0;
        for(int i = 0; i < cinema.length; i++) {
            for (int j = 0; j < cinema.length; j++){
                char x = cinema[i][j];
                int y = 0;
                if(x == 'B') {
                    t++;
                    if(rows * seats <= 60){
                        y = 10;
                    }else if(rows % 2 == 0 && i + 1 <= rows / 2 && rows * seats > 60){
                        y = 10;
                    }else if(rows % 2 == 0 && i + 1 > rows / 2 && rows * seats > 60){
                        y = 8;
                    }else if(rows % 2 != 0 && i + 1 <= (rows / 2) && rows * seats > 60){
                        y = 10;
                    }else if(rows % 2 != 0 && i + 1 >= (rows / 2)  && rows * seats > 60) {
                        y = 8;
                    }
                }
                currentIncome += y;
            }
        }
        System.out.println("Number of purchased tickets: "+ t);
        double percent;
        if(t != 0){
            percent = (double) t / (rows * seats) * 100;
        }else{
            percent = 0;
        }
        String percentages = String.format("%.2f", percent);
        System.out.println("Percentage: "+ percentages + "%");
        System.out.println("Current income: $" + currentIncome);
    }
}