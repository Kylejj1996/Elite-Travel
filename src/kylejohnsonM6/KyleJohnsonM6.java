//Kyle Johnson 
//Mastery 6 Multithreading
//11-12-2024

//Link for Color Scheme
//https://colordesigner.io/color-scheme-builder?mode=lch#254956-E64833-964F40-FBE9D0-8FAEAD
package kylejohnsonM6;

public class KyleJohnsonM6 {

    public static void main(String[] args) {
      
        //Creating objects for the states
        State.loginState = new LoginState();
        State.employeeView = new EmployeeView();
        State.managerView = new ManagerView();
        State.customerView = new CustomerView();
        State.current = State.loginState;//Starting the program in the loginState 
        
        
        
        
//        while (true) {
//            State.current.enter();
//            State.current.update();
//        }

    }

}
