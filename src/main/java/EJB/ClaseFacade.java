/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import modelo.Clase;
import modelo.Usuario;

import java.util.ArrayList;

/**
 *
 * @author Administrador
 */
@Stateless
public class ClaseFacade extends AbstractFacade<Clase> implements ClaseFacadeLocal {

    @PersistenceContext(unitName = "AerolineaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClaseFacade() {
        super(Clase.class);
    }

    /**
     * Buscar
     * @param by Buscar por
     * @param text Texto a buscar
     * @return Lista de clases encontradas
     */
    private ArrayList<Clase> searchBy(String by, String text) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Clase> cq;

            if (by.equals("name")) {
                cq = cb.createQuery(Clase.class);
                Root<Clase> clase = cq.from(Clase.class);
                cq.select(clase).where(cb.like(clase.get("nombre"), "%" + text + "%"));
            } else {
                System.out.println("Search by " + by + " not supported");
                return new ArrayList<>();
            }

            return new ArrayList<>(em.createQuery(cq).getResultList());
        } catch (Exception e) {
            System.out.println("Hubo un error buscando en clases: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public ArrayList<Clase> searchByName(String name) {
        return searchBy("name", name);
    }
}
