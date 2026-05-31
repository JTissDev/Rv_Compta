package com.jtissdev.features.referential.repository;

import com.jtissdev.features.referential.dto.ReferentialCoreDTO;

import java.util.Optional;

/**
 *
 * @author jtiss
 * @since 0.6
 * @version 1.0
 * ToDo javadoc with IA
 */
public interface ReferentialInterface {


	void save(ReferentialCoreDTO referentialCoreDTO);
	void update(ReferentialCoreDTO referentialCoreDTO);
	void delete(ReferentialCoreDTO referentialCoreDTO);

	Optional<ReferentialCoreDTO> load();

}
