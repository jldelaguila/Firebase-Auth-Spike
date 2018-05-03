package com.everis.domain.repository;

import com.everis.domain.model.LocalContact;

import java.util.List;

import rx.Observable;

/**
 * Created by everis on 3/05/18.
 */

public interface ContentProviderRepository {

    Observable<List<LocalContact>> getLocalContactsBatch();

}
