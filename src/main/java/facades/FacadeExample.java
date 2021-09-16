package facades;

import dtos.RenameMeDTO;
import entities.RenameMe;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import utils.EMF_Creator;


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
    
    public RenameMeDTO create(RenameMeDTO rm){
        RenameMe rme = new RenameMe(rm.getDummyStr1(), rm.getDummyStr2());
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(rme);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new RenameMeDTO(rme);
    }
    public RenameMeDTO getById(long id){
        EntityManager em = emf.createEntityManager();
        return new RenameMeDTO(em.find(RenameMe.class, id));
    }
    
    //TODO Remove/Change this before use
    public long getRenameMeCount(){
        EntityManager em = emf.createEntityManager();
        try{
            return (long)em.createQuery("SELECT COUNT(r) FROM RenameMe r").getSingleResult();
        }finally{  
            em.close();
        }
    }
    
    public List<RenameMeDTO> getAll(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<RenameMe> query = em.createQuery("SELECT r FROM RenameMe r", RenameMe.class);
        List<RenameMe> rms = query.getResultList();
        return RenameMeDTO.getDtos(rms);
    }

    //TODO: refactor this deletemethod

//    public AnimalDTO delete(long id) throws AnimalNotFoundException {
//        EntityManager em = emf.createEntityManager();
//
//        Animal a = em.find(Animal.class, id);
//        if (a == null) {
//            throw new AnimalNotFoundException("Animal with id :" + id + "does not exist");
//        }
//
//        try {
//            em.getTransaction().begin();
//            em.remove(a);
//            em.getTransaction().commit();
//        }  finally { em.close();
//        }
//        return new AnimalDTO(a);
//    }

}
