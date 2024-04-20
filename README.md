import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

public class w2051596_PlaneManagement {
    private static final Person[] tickets=new Person[52];
    private static final Ticket[] ticketInfo=new Ticket[52];
    private static String firstName;
    private  static String sureName;
    private static String email;
    private static int count=0;
    private static final String [] seatNumbers=new String[52];
    private static String[][] bookingSeats;
    private static String rowLetter;
    private static int columnNumber;

    public static void main(String[] args) {
        String[][] bookingSeats = new String[5][15];

        bookingSeats = Available_seats();   //calling the seat structure array
        System.out.println();
        System.out.println("|                Welcome To Plane Management Application.                          |");
        System.out.println("                -----------------------------------------");
        int option=0;

        do {
            try {

                System.out.println("***********************************************************************************");
                System.out.println("*                              MENU OPTIONS                                       *");
                System.out.println("***********************************************************************************");
                System.out.println("1) Buy a seat\n2) Cancel a seat\n3) Find first Available seat\n4) Show seating plan\n5) Print tickets information and total sales");
                System.out.println("6) Search ticket\n0) Quit");
                System.out.println("***********************************************************************************");
                System.out.print("Select an option.Enter Number : ");
                Scanner scanner = new Scanner(System.in);
                option = scanner.nextInt();
                if (option > 7 || option < 0) {
                    throw new IllegalArgumentException ("option is out of range!.Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.Enter a valid option number.\n");
                option = -1;
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage()+"\n");
            }
            switch (option) {
                case 1:
                    System.out.println("Seat Booking Page\n");
                    System.out.println("Use a row letter and  a column number to book a seat.Prices are shown below according to letters.\n");
                    seatStructure();
                    buy_seat(firstName, sureName, email);
                    break;
                case 2:
                    System.out.println("seat Cancelling Page.");
                    System.out.println("Use a row letter and  a column number to cancel a seat.\n");
                    cancel_seat(bookingSeats);
                    break;
                case 3:
                    System.out.println("Find First Available Seat.\n");
                    find_first_available(bookingSeats);
                    System.out.println();
                    break;
                case 4:
                    System.out.println("Seating Plan.");
                    show_seating_plan(bookingSeats);
                    System.out.println();
                    break;
                case 5:
                    System.out.println("Billing Information.");
                    print_tickets(tickets,ticketInfo);
                    total_price(bookingSeats);
                    break;
                case 6:
                    System.out.println("Use a row letter and  a column number to find whether a seat is available or not.");
                    search_tickets(seatNumbers,tickets);
                    break;
                case 0:
                    System.out.println("\nFor further details contact support assistance.\nThank you for connecting with us!. ");
                    break;
            }
        } while (option != 0);
    }
    //Method to print seat structure to user
    public static void seatStructure() {
        String[] column = {"  ", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14"};

        String[][]planeSeatStructure = {{"   ","01","02","03","04","05","06","07","08","09","10","11","12","13","14"},
                {"A  ","Y ", "Y ", "Y ", "Y ", "Y ", "B ", "B ", "B ", "B ", "G ", "G ", "G ", "G ", "G "},
                {"B  ","Y ","Y ","Y ","Y ","Y ","B ","B ","B ","B ","G ","G ","G "},
                {"C  ","Y ","Y ","Y ","Y ","Y ","B ","B ","B ","B ","G ","G ","G "},
                {"D  ","Y ","Y ","Y ","Y ","Y ","B ","B ","B ","B ","G ","G ","G ","G ","G "}
        };
        for (int i=0;i<planeSeatStructure.length;i++){
            for (int j = 0; j< planeSeatStructure[i].length; j++){
                System.out.print(" "+planeSeatStructure[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println("Y = $ 200\nB = $ 150\nG = $ 180");

    }
    private static void buy_seat(String firstName, String sureName, String email) {

        getting_inputs(); //calling method to getting user inputs
        String searchValue = "X ";
        int index=calculating_index();
        if (bookingSeats[index][columnNumber].equals(searchValue)) {
            System.out.println("Seat is not available.\n");
        } else {
            System.out.println("Seat is available.");
            String convertedColumnNumber=String.valueOf(columnNumber);
            String seatNumber = rowLetter.toUpperCase() + convertedColumnNumber; //define seat as a String to use in methods.
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please fill the following information to confirm the ticket.");
            boolean inputMissMatch1=false;
            while(true){
                System.out.print("First Name : ");
                firstName = scanner.next();
                String firstNameRegex = "[a-zA-Z]{2,}"; //Use regex to format user inputs
                if(firstName.matches(firstNameRegex)){
                    break;
                }else {
                    System.out.println("Invalid input.Please try again.\n");
                }
            }
            while(true){
                System.out.print("Sure name : ");
                sureName = scanner.next();
                String sureNameRegex= "[a-zA-Z]{2,}";  //use a regex to format user inputs.
                if(sureName.matches(sureNameRegex)){
                    break;
                }else {
                    System.out.println("Invalid input.Please try again.");
                }
            }
            while(true){
                System.out.print("e-mail address : ");
                email = scanner.next();
                String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
                if(email.matches(emailRegex)){ //used a regex to format user inputs.
                    break;
                }else{
                    System.out.println("Please enter a valid email address");//throw exception to catch as an error
                }
            }
            seatNumbers[count]= seatNumber;  //creating array that included booked seats.
            bookingSeats[index][columnNumber] = "X ";
//adding price automatically into the Ticket
            int totalSum = 0;
            for (int i = 1; i < bookingSeats.length; i++) {
                for (int j = 1; j < bookingSeats[i].length; j++) {
                    if (bookingSeats[i][j].equals(bookingSeats[index][columnNumber])) {
                        if (j > 0 && j < 6) {
                            totalSum = 200;
                        } else if (j > 5 && j < 10) {
                            totalSum = 150;
                        } else if (j > 9 && j < 13) {
                            totalSum = 180;
                        } else if (i == 1 && j > 12) {
                            totalSum = 180;
                        } else if (i == 4 && j > 12) {
                            totalSum = 180;
                        } else {
                            System.out.println("unexpected error occurred\n");
                        }
                        break;
                    }
                }
            }
//saving information to arrays
            if( (firstName != null) || (sureName != null) || (email != null)){  //checking weather user inputs are assigned to variables
                Person person=new Person(firstName,sureName,email);             //to stop getting unexpected errors.
                tickets[count]= person;
            }else{
                System.out.println("Sorry! cannot save ticket information.Please cancel this ticket and try again\n");
            }
            if((rowLetter!=null) || (columnNumber!=0) || (totalSum!=0)){
                Ticket newTicket =new Ticket(rowLetter.toUpperCase(),columnNumber,totalSum);
                ticketInfo[count]=newTicket;
            }else{
                System.out.println("Sorry! cannot save ticket information.Please cancel this ticket and try again\n");
            }
            try{
                save(seatNumber);
            }catch (NullPointerException e){
                System.out.println("Sorry! cannot save ticket information.Please cancel this ticket and try again\n");
            }
            count ++;
        }
    }
    /* Adapted from[http://www.java2s.com/example/java/regular-expressions/input-and-validate-data-from-user-using-regular-expression.html]
      code=regex(line 143,125,158)
      modification-arranged the code  according to my preferences**/
    //Method to create seat structure for programming purposes.
    public static String[][] Available_seats() {
        String[] column = {"  ", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14"};
        bookingSeats = new String[5][15];
        for (int i=0;i<column.length;i++){       //To add column numbers
            bookingSeats[0][i]=column[i];
        }
        String [] rowMarks = {"  ","A ","B ","C ","D "}; //To add seats and row characters
        for(int r = 0;r<rowMarks.length;r++){
            bookingSeats[r][0]=rowMarks[r];
        }
        for (int i = 1; i < bookingSeats.length; i++) {          //defining seat plan as non-booked
            for (int j = 0; j < bookingSeats[i].length; j++) {
                if((i==2 || i==3) && (j==13 || j==14)){
                    bookingSeats[i][j] = "  ";
                }else if(j!=0){
                    bookingSeats[i][j] = "O ";
                }
            }
        }
        return bookingSeats;
    }
    private static void cancel_seat(String[][] bookingSeat) {
        getting_inputs();
        String searchValue = "X ";  //searching weather seat is booked
        int index=calculating_index();

        String stringColumnNumber=String.valueOf(columnNumber);
        String check= rowLetter.toUpperCase()+stringColumnNumber;
        if (bookingSeat[index][columnNumber].equals(searchValue)) {
            bookingSeat[index][columnNumber] ="O ";
            for (int x=0;x<seatNumbers.length;x++){
                if(check.equals(seatNumbers[x])) {
                    tickets[x] = null;
                    seatNumbers[x] = null;    //removing information from arrays
                    ticketInfo[x] = null;
                    break;
                }
            }
            String fileName = check+".txt";
            File fileToDelete=new File(fileName);
            if(fileToDelete.exists()){              //deleting file created before.
                fileToDelete.delete();
            }else{
                System.out.println("Unable to cancel the seat.Please try again.\n");
            }
            System.out.println("Seat "+check+ " cancelled successfully.\n");
        } else {
            System.out.println("Seat not booked.\n");
        }
    }
    public static void find_first_available(String[][]bookingSeats){
        String searchValue = "O ";
        boolean find = false;
        for(int i=1;i<bookingSeats.length;i++) {
            for (int j = 1; j < bookingSeats[i].length; j++){
                if (bookingSeats[i][j].equals(searchValue)){
                    find=true;
                    switch (i){
                        case 1:
                            System.out.println("First available seat  : A"+j);
                            break;
                        case 2:
                            System.out.println("First available seat : B"+j);
                            break;
                        case 3:
                            System.out.println("First available seat : C"+j);
                            break;
                        case 4:
                            System.out.println("First available seat : D"+j);
                            break;
                    }
                    return;
                }
            }
        }
        if(!find){
            System.out.println("There are no available seats.\n");
        }
    }
    public static void show_seating_plan(String[][]seats) {
        for (String[] rows : seats) {
            for (String columns : rows) {
                System.out.print(" " + columns + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    public static void print_tickets(Person[] Tickets,Ticket[] ticketInfo){
        for (int i=0;i<tickets.length;i++) {
            if (tickets[i]!=null){
                System.out.println("* Air Ticket *");
                System.out.println("--------------");
                System.out.println("First name : " + tickets[i].getName());
                System.out.println("Sure name : " + tickets[i].getSureName());
                System.out.println("e-mail : " + tickets[i].getEmail()+"\n");
            }
            if(ticketInfo[i]!=null){
                System.out.println("*Ticket Information");
                System.out.println("seat row letter : "+ticketInfo[i].getRow());
                System.out.println("seat column number : "+ticketInfo[i].getSeat());
                System.out.println("Ticket Price : $ "+ticketInfo[i].getPrice()+"\n");
            }
        }
    }
    public static void total_price(String[][]bookingSeats) {
        String searchValue = "X ";
        int totalSum = 0;
        for (int i = 1; i < bookingSeats.length; i++) {
            for (int j = 1; j < bookingSeats[i].length; j++) {
                if (bookingSeats[i][j].equals(searchValue)) {
                    if (j > 0 && j < 6) {
                        totalSum += 200;
                    } else if (j > 5 && j < 10) {
                        totalSum += 150;
                    } else if (j > 9 && j < 13) {
                        totalSum += 180;
                    } else if (i == 1) {
                        totalSum += 180;
                    } else if (i == 4 && j > 12) {
                        totalSum += 180;
                    } else {
                        System.out.println("unexpected error occurred");
                    }
                }
            }
        }
        System.out.println("Total tickets price is : $ "+totalSum);
    }
    public static void search_tickets(String[]seatNumbers,Person[]tickets){

        getting_inputs();
        String seatNumber1=String.valueOf(columnNumber);
        String seatToFind = rowLetter.toUpperCase()+seatNumber1;
        boolean printed = false;
        for(int i=0;i< seatNumbers.length;i++){
            if(seatNumbers[i]!=null && seatNumbers[i].equals(seatToFind)){
                printed=true;
                if (tickets!=null){
                    System.out.println("Seat owner's information : ");
                    System.out.println("First name : " + tickets[i].getName());
                    System.out.println("Sure name : " + tickets[i].getSureName());
                }if(ticketInfo!=null){
                    System.out.println("Ticket Price : $ "+ticketInfo[i].getPrice()+"\n");
                    break;
                }
            }else{
                continue;
            }
        }if(!printed){
            System.out.println("seat is available\nDo you want to book this seat?.Enter option 1.");
        }
    }
    public static void save(String seatNumber){
        String fileName;
        fileName = seatNumber;
        try{
            File file = new File(fileName+".txt");
            boolean file_created = file.createNewFile();
        }catch (IOException e){
            System.out.println("Unable to confirm booking.Please cancel this ticket and try again.\n");
        }try{
            FileWriter file = new FileWriter(fileName+".txt");
            if (tickets!=null){
                file.write("Seat owner's information : ");
                file.write("\nFirst name : " + tickets[count].getName());
                file.write("\nSure name : " + tickets[count].getSureName());
                file.write("\ne-mail : " + tickets[count].getEmail());
            }
            if(ticketInfo!=null) {
                file.write("\nTicket row letter : " + ticketInfo[count].getRow());
                file.write("\nTicket column number : " + ticketInfo[count].getSeat());
                file.write("\nTicket price : $ " + ticketInfo[count].getPrice());
            }
            file.close();
            System.out.println("Booking confirmed.Do you want to see Billing Information ?.Enter option 5. \n");
        }catch (IOException e){
            System.out.println("Error while saving data...Please try again.");
        }
    }
    //method to getting inputs
    public static void getting_inputs() {

        while (true) {
            System.out.println();
            System.out.print("Enter a row letter : ");
            Scanner scanner = new Scanner(System.in);
            rowLetter = scanner.next();
            if ("ABCD".contains(rowLetter.toUpperCase())) {
                break;
            } else {
                System.out.println("Invalid row letter!.Try again");
            }
        }
        while (true) {
            Scanner scanner = new Scanner(System.in);
            try {
                System.out.print("Enter a seat column number : ");
                columnNumber = scanner.nextInt();
                if (columnNumber >= 1 && columnNumber < 15) {
                    break;
                } else {
                    System.out.println("Invalid column number!.Try again");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input invalid!");
            }
            System.out.println();
        }
    }

    public static int calculating_index(){
        int index = 0;
        if (rowLetter.equalsIgnoreCase("A")) {
            index = 1;
        } else if (rowLetter.equalsIgnoreCase("B")) {
            index = 2;
        } else if (rowLetter.equalsIgnoreCase("C")) {
            index = 3;
        } else if (rowLetter.equalsIgnoreCase("D")) {
            index = 4;
        }
        return index;
    }
}


