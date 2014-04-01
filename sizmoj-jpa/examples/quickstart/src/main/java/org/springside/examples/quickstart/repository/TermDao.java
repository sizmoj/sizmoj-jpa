package org.springside.examples.quickstart.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springside.examples.quickstart.entity.Task;
import org.springside.examples.quickstart.entity.Term;

public interface TermDao extends PagingAndSortingRepository<Term, Long>, JpaSpecificationExecutor<Term> {

}
