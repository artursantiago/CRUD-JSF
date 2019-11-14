package repositories;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author sinfo-anexo
 *
 *Classe que inicia a comunicação com o banco de dados.
 */
public abstract class AbstractRepository {
	
	/**
	 * Inicializa o EntityManagerFactory
	 */
	static EntityManagerFactory factory = null;
	
	/**
	 * Método para criar um EntityManager. 
	 */
	public EntityManager createEntityManager() { 
		if(factory == null) {
			factory = Persistence.createEntityManagerFactory("universidade");
		}
		return factory.createEntityManager();
	}
}
