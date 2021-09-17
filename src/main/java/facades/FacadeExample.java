package facades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


public class FacadeExample {

    private static FacadeExample instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private FacadeExample() {}

    public static FacadeExample getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new FacadeExample();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    

//    public RenameMeDTO getById(long id){
//        EntityManager em = emf.createEntityManager();
//        return new RenameMeDTO(em.find(testentity.class, id));
//    }
    
    //TODO Remove/Change this before use
    public long getRenameMeCount(){
        EntityManager em = emf.createEntityManager();
        try{
            return (long)em.createQuery("SELECT COUNT(r) FROM testentity r").getSingleResult();
        }finally{  
            em.close();
        }
    }
    


    //TODO: refactor this deletemethod



}
