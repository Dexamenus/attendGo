/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.attendgo;

import object.karyawan;

/**
 *
 * @author HP VICTUS
 */
public class AttendGo {

    public static void main(String[] args) {
        karyawan KR = new karyawan();
        if(KR instanceof karyawan){
            System.err.println("Karyawan");
        }else {
            System.err.println("Something else");
        }// //
        // //
    }
}
