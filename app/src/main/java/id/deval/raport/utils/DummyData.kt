package id.deval.raport.utils

import id.deval.raport.db.models.Account
import id.deval.raport.db.models.Siswa

class DummyData {
    fun setDummyGuru(): ArrayList<Account> {
        val dataGuru = arrayListOf<Account>()
        for (n in 1..5) {
            val guru = Account(
                "980213088132",
                "ArogaN61",
                "guru",
                "Jln. S. Sutoyo",
                "ArogaN61",
                "01288312",
                "Ali",
                "ali@gmail.com",
                "ArogaN61"
            )
            dataGuru.add(guru)
        }
        return dataGuru
    }

    fun setDummyDataSiswa(): ArrayList<Siswa> {
        val dataSiswa = arrayListOf<Siswa>()
        for (n in 1..5) {
            val siswa = Siswa(
                "980213088132",
                "Jln Sutoyo",
                "-",
                "Laki-laki",
                "-",
                "17 Juni 1999",
                "Jln. Wali",
                "Islam",
                "Ali",
                "asdasdas",
                "-",
                "Syam",
                "0192839",
                "Ali Asgar",
                "60200117039",
                "Onto",
                "PNS",
                "PNS"
            )
            dataSiswa.add(siswa)
        }
        return dataSiswa
    }
}