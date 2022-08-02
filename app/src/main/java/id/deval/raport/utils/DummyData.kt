package id.deval.raport.utils

import id.deval.raport.db.models.Account
import id.deval.raport.db.models.Kelas
import id.deval.raport.db.models.Mapel
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

    fun setDummyDataKelas(): ArrayList<Kelas> {
        val dataKelas = arrayListOf<Kelas>()
        for (n in 1..5) {
            val siswa = Kelas(
                "980213088132",
                arrayListOf(),
                "Kelas IV A",
                arrayListOf(),
                1,
                "2021/2022",
                "912039812m3",
                )
            dataKelas.add(siswa)
        }
        return dataKelas
    }

    fun setDummyDataMapel(): ArrayList<Mapel> {
        val dataMapel = arrayListOf<Mapel>()
        for (n in 1..5) {
            val siswa = Mapel(
                "asdasdasd",
                "Matematika",
                "Khusus"
                )
            dataMapel.add(siswa)
        }
        return dataMapel
    }
}