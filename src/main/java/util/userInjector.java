/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import services.AuthServices;

/**
 *
 * @author HP VICTUS
 */
public class userInjector {
        public static void main(String[] args) {
        AuthServices userService = new AuthServices();
        userService.registerUser("abdandappa", "dappa", "123"); 
    }
}
