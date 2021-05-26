package com.ray.study.smaple.beanmap.mapstruct.converter;

import java.util.List;

/**
 * description
 *
 * @author r.shi 2021/5/26 16:42
 */
public interface BaseConverter<E, D> {

    /**
     * E to D
     * @param e /
     * @return /
     */
    D toD(E e);

    /**
     * D to E
     * @param d /
     * @return /
     */
    E toE(D d);

    /**
     * E to D
     * @param e /
     * @return /
     */
    List<D> toD(List<E> e);

    /**
     * D to E
     * @param d /
     * @return /
     */
    List<E> toE(List<D> d);


}