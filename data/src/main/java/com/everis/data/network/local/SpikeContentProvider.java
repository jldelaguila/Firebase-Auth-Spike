package com.everis.data.network.local;

import com.everis.data.model.LocalContactEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by everis on 3/05/18.
 */

public interface SpikeContentProvider {

    Observable<List<LocalContactEntity>> getLocalContatsBatch();

}
