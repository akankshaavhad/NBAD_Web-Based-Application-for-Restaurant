/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assignment2Serv;

import com.assignment2data.MailUtilGmail;
import java.io.IOException;
import java.io.PrintWriter;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author aavha
 */
public class EmailListServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // get current action
        String action = request.getParameter("action");
        if (action == null) {
            action = "join";  // default action
        }

        // perform action and set URL to appropriate page
        String url = "/index.jsp";        
        if (action.equals("join")) {
            url = "/index.jsp";    // the "join" page
        } 
        else if (action.equals("addcontact")) {
            // get parameters from the request
            String username = request.getParameter("nameuser");
            String email = request.getParameter("email");
            String message = request.getParameter("message");

            // store data in User object
          //  User user = new User(firstName, lastName, email);
           // UserDB.insert(user);
            //request.setAttribute("user", user);
            
            // send email to user
           String to = email;
            String to1 = "aavhad@uncc.edu";
            String from = "email_list@murach.com";
            String subject = "Welcome to our email list";
            String subject1 = "Customer Request";
            String body = "Dear " + username + ",\n"
                    + "Thank you for your request. We will contact you soon."
                    + "\n\n Regards,\n Akanksha+William";
            String body1 = "Custome Request:" + message + "\n Regards," + username;
            boolean isBodyHTML = false;

                           
            try
            {
                MailUtilGmail.sendMail(to, from, subject, body, isBodyHTML);
                MailUtilGmail.sendMail(to1, from, subject1, body1, isBodyHTML);
            
            }
            catch (MessagingException e)
            {
                String errorMessage = 
                    "ERROR: Unable to send email. " + 
                        "Check Tomcat logs for details.<br>" +
                    "NOTE: You may need to configure your system " + 
                        "as described in chapter 14.<br>" +
                    "ERROR MESSAGE: " + e.getMessage();
                request.setAttribute("errorMessage", errorMessage);
                this.log(
                    "Unable to send email. \n" +
                    "Here is the email you tried to send: \n" +
                    "=====================================\n" +
                    "TO: " + email + "\n" +
                    "FROM: " + from + "\n" +
                    "SUBJECT: " + subject + "\n" +
                    "\n" +
                    body + "\n\n");
            }            
            url = "/confirmcontact.jsp";
        }
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }    
}