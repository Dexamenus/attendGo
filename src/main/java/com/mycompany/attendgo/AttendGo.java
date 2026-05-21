/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.attendgo;

import object.Karyawan1;

/**
 *
 * @author HP VICTUS
 */
public class AttendGo {

    public static void main(String[] args) {
        Karyawan1 KR = new Karyawan1();
        if(KR instanceof Karyawan1){
            System.err.println("Karyawan");
        }else {
            System.err.println("Something else");       
        }// //
        // //
    }
}
