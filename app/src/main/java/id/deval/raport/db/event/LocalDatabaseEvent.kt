package id.deval.raport.db.event

data class LocalDatabaseEvent<T>(
    val data : T,
    val type:String
)
