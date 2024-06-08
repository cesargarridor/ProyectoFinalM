# CAMBIOS REALIZADOS EN EL PROYECTO

### UserViewModel
Se eliminaron las líneas que inicializaban una variable user como null ya que era innecesario y se simplificó el bloque de código eliminando la condición if (!email.isEmpty() && !password.isEmpty()).

`UserViewModel original`
```
     fun isLogin(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user: User? = null
            if (!email.isEmpty() && !password.isEmpty()) {
                val user = useCaseLogin.login(email, password) //invocamos al usuario.
            }
          
            withContext(Dispatchers.Main) {//Con Dispatchers.Main, indicamos que el hilo se ejecute en el principal.
                login.postValue(user)
            }
        }
    }

```


`UserViewModel cambiado`
```
  fun isLogin(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = useCaseLogin.login(email, password)

            withContext(Dispatchers.Main) {
                login.postValue(user)
            }
        }
    }

```
### UserDataBaseSingleton

Se reemplazó la condición if (database == null) por if (!::database.isInitialized). Esto verifica si database ya ha sido inicializado o no, y se eliminó la asignación de userDao dentro del bloque if, ya que userDao es una variable de clase y se puede acceder directamente sin necesidad de inicializarla dentro de un bloque condicional.

`UserDataBaseSingleton original`
```
 fun init(context: Context){
        synchronized(this){
            if (database == null){
                database = Room.databaseBuilder(
                    context.applicationContext,
                    UserDataBase::class.java,
                    "my_app_user"

                ).build()
                userDao = database!!.userDao()
            }
        }
    }

```

`UserDataBaseSingleton cambiado`
```
 fun init(context: Context) {
        synchronized(this) {
            if (!::database.isInitialized) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    UserDataBase::class.java,
                    "my_app_user"
                ).build()
                userDao = database.userDao()
            }
        }
    }

```

### UserDataBaseSingleton
Aqui solo he añadido usuarios de prueba

`UserDataBase original`
```
@Database(entities = [UserEntity::class], version = 1)
abstract class UserDataBase : RoomDatabase(){
    abstract fun userDao(): UserDao
}

```
`UserDataBase modificado`
```
@Database(entities = [UserEntity::class], version = 1)
abstract class UserDataBase : RoomDatabase(){
    abstract fun userDao(): UserDao
    companion object {

        //he creado este metodo para insertar usuarios de prueba
        private suspend fun insertTestUsers(userDao: UserDao?) {
            val testUsers = listOf(
                UserEntity(id = 1, name = "a", email = "a", password = "a", phone = "123456789", imag = "a"),
                UserEntity(id = 2, name = "aa", email = "aa", password = "a", phone = "987654321", imag = "a")
            )

            userDao?.insertUser(*testUsers.toTypedArray())
        }
    }
}
```


Tambien hice pequeños cambios en el manifest para que se iniciase primeramente el activityLogin, y un pequeño error en el layout del login.