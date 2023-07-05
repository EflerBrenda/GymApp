package com.efler.gymapp.request;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import android.content.Context;
import android.content.SharedPreferences;

import com.efler.gymapp.modelo.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public class ApiRetrofit {

    private static final String PATH="http://192.168.0.103:5000/api/";
    private static ServiceGym servicioGym;

    public static ServiceGym getServiceGym(){

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(PATH)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        servicioGym=retrofit.create(ServiceGym.class);

        return servicioGym;
    }
    public static String obtenerToken(Context context){
        SharedPreferences sp= context.getSharedPreferences("token",0);
        String token=sp.getString("token","No hay datos");
        return token;
    }
    public static void logOut(Context context){
        SharedPreferences sp= context.getSharedPreferences("token",0);
        sp.edit().clear();
    }
    public static int obtenerUsuarioActual(Context context){
        SharedPreferences sp= context.getSharedPreferences("UsuarioActual",0);
        int id=sp.getInt("id",0);
        return id;
    }
    public static String obtenerNombreUsuarioActual(Context context){
        SharedPreferences sp= context.getSharedPreferences("UsuarioActual",0);
        String nombreApellido =sp.getString("nombreApellido","");
        return nombreApellido;
    }
    public static int obtenerRolActual(Context context){
        SharedPreferences sp= context.getSharedPreferences("UsuarioActual",0);
        int rolid=sp.getInt("rolid",0);
        return rolid;
    }
    public static int obtenerPlan(Context context){
        SharedPreferences sp= context.getSharedPreferences("UsuarioActual",0);
        int planid=sp.getInt("planid",0);
        return planid;
    }

    public interface ServiceGym {

        @FormUrlEncoded
        @POST("usuarios/login")
        Call<String>login (@Field("Email") String email,@Field("Password") String password);

        @GET("usuarios/ObtenerPerfil")
        Call<Usuario> obtenerPerfil (@Header("Authorization") String token);

        @FormUrlEncoded
        @POST("usuarios/PedidoEmail")
        Call<Usuario> reestablecerPassword (@Field("email") String email);

        @GET("anuncios/obtenerAnuncios")
        Call<List<Anuncio>> obtenerAnuncios (@Header("Authorization") String token);

        @FormUrlEncoded
        @PUT("usuarios/cambiarPassword")
        Call<Usuario> cambiarPassword (@Header("Authorization") String token,@Field("PasswordActual") String passwordActual,@Field("PasswordNueva") String passwordNueva);


        @PUT("anuncios/EditarAnuncio")
        Call<Anuncio> editarAnuncio (@Header("Authorization") String token, @Body Anuncio anuncio);

        @DELETE("anuncios/BajaAnuncio/{id}")
        Call<Anuncio> bajaAnuncio (@Header("Authorization") String token,@Path("id") int id);


        @POST("anuncios/NuevoAnuncio")
        Call<Anuncio> nuevoAnuncio (@Header("Authorization") String token,@Body Anuncio anuncio);

        @GET("categorias/ObtenerCategorias")
        Call <List<Categoria>> obtenerCategorias (@Header("Authorization") String token);

        @GET("Ejercicios/EjerciciosPorCategorias/{id}")
        Call <List<Ejercicio>> EjerciciosPorCategorias (@Header("Authorization") String token,@Path("id") int id);

        @POST("Ejercicios/NuevoEjercicio")
        Call<Ejercicio> nuevoEjercicio (@Header("Authorization") String token,@Body Ejercicio ejercicio);

        @PUT("Ejercicios/EditarEjercicio")
        Call<Ejercicio> editarEjercicio (@Header("Authorization") String token,@Body Ejercicio ejercicio);

        @DELETE("Ejercicios/BajaEjercicio/{id}")
        Call<Ejercicio> bajaEjercicio (@Header("Authorization") String token,@Path("id") int id);

        @GET("usuarios/ObtenerAlumnos")
        Call <List<Usuario>> obtenerAlumnos (@Header("Authorization") String token);

        @POST("usuarios/NuevoAlumno")
        Call<Usuario> nuevoAlumno (@Header("Authorization") String token,@Body Usuario alumno);

        @PUT("usuarios/editarAlumno")
        Call<Usuario> editarAlumno  (@Header("Authorization") String token,@Body Usuario alumno);

        @DELETE("usuarios/bajaAlumno/{id}")
        Call<Usuario> bajaAlumno  (@Header("Authorization") String token,@Path("id") int id);

        @GET("planes/obtenerPlanes")
        Call <List<Plan>> obtenerPlanes(@Header("Authorization") String token);

        @POST("planes/nuevoPlan")
        Call<Plan> nuevoPlan (@Header("Authorization") String token,@Body Plan plan);

        @PUT("planes/EditarPlan")
        Call<Plan> editarPlan  (@Header("Authorization") String token,@Body Plan plan);

        @DELETE("planes/BajaPlan/{id}")
        Call<Plan> bajaPlan  (@Header("Authorization") String token,@Path("id") int id);

        @GET("rutinas/obtenerRutinas")
        Call <List<Rutina>> obtenerRutinas(@Header("Authorization") String token);

        @GET("rutinas/obtenerCantDiasRutina")
        Call <List<Integer>> obtenerCantDiasRutina(@Header("Authorization") String token);

        @POST("rutinas/nuevoRutina")
        Call<Rutina> nuevaRutina(@Header("Authorization") String token,@Body Rutina rutina);

        @PUT("rutinas/EditarRutina")
        Call<Rutina> editarRutina  (@Header("Authorization") String token,@Body Rutina rutina);

        @DELETE("rutinas/BajaRutina/{id}")
        Call<Rutina> bajaRutina  (@Header("Authorization") String token,@Path("id") int id);

        @GET("usuarios/ObtenerProfesores")
        Call <List<Usuario>> obtenerProfesores (@Header("Authorization") String token);

        @GET("asistencia/ObtenerAsistenciasAlumnos")
        Call <List<Asistencia>> obtenerAsistencia(@Header("Authorization") String token);

        @POST("asistencia/NuevaAsistencia")
        Call<Asistencia> nuevaAsistencia(@Header("Authorization") String token);

        @GET("categorias/ObtenerCategoriasDia/{dia}")
        Call <List<Categoria>> obtenerCategoriasDia (@Header("Authorization") String token,@Path("dia") int dia);

    }
}
