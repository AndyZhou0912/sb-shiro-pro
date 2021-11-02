package wnxy.wn44.ssp.mapper;

public class LazySingleton {
    private LazySingleton(){};
    private static class SingletonHandler{
        private static LazySingleton ls =new LazySingleton();
    }
    public static LazySingleton getInstance(){
        return SingletonHandler.ls;
    }
}
