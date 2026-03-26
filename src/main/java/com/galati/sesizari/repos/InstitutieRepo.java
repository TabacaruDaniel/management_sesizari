
package com.galati.sesizari.repos;

import com.galati.sesizari.clase.Institutie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstitutieRepo extends JpaRepository<Institutie,Long> {
    // Această metodă este "magia" care face legătura.
    // Numele metodei TREBUIE să conțină numele exact al variabilei din clasa Institutie
    // (în cazul tău, ai variabila 'numeInstitutie')
    Institutie findByNumeInstitutie(String numeInstitutie);
}
