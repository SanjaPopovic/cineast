package org.vitrivr.cineast.api.rest.services;

import org.vitrivr.cineast.api.messages.components.MetadataDomainFilter;
import org.vitrivr.cineast.api.messages.components.MetadataKeyFilter;
import org.vitrivr.cineast.core.data.entities.MediaObjectMetadataDescriptor;
import org.vitrivr.cineast.core.db.dao.reader.MediaObjectMetadataReader;
import org.vitrivr.cineast.standalone.config.Config;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * This is a utility class to retrieve metadata in various forms.
 * <p>
 * TODO: Name and location to be discussed
 */
public class MetadataRetrievalService {
  
  private final MediaObjectMetadataReader reader;
  
  private final boolean autoclose;
  
  public MetadataRetrievalService() {
    this(true);
  }
  
  public MetadataRetrievalService(final boolean autoclose) {
    this.autoclose = autoclose;
    this.reader = new MediaObjectMetadataReader(Config.sharedConfig().getDatabase().getSelectorSupplier().get());
  }
  
  public List<MediaObjectMetadataDescriptor> find(final String objectId, final String domain, final String key) {
    return lookupMultimediaMetadata(objectId).stream().filter(createDomainAndKeyFilter(domain, key)).collect(Collectors.toList());
  }
  
  public List<MediaObjectMetadataDescriptor> findByKey(final String objectId, final String key) {
    final MetadataKeyFilter predicate = MetadataKeyFilter.createForKeywords(key);
    return lookupMultimediaMetadata(objectId).stream().filter(predicate).collect(Collectors.toList());
  }
  
  public List<MediaObjectMetadataDescriptor> findByKey(final List<String> objectIds, final String key){
    final MetadataKeyFilter filter = MetadataKeyFilter.createForKeywords(key);
    return lookupMultimediaMetadata(objectIds).stream().filter(filter).collect(Collectors.toList());
  }
  
  public List<MediaObjectMetadataDescriptor> findByDomain(final String objectId, final String domain) {
    final MetadataDomainFilter predicate = MetadataDomainFilter.createForKeywords(domain);
    return lookupMultimediaMetadata(objectId).stream().filter(predicate).collect(Collectors.toList());
  }
  
  public List<MediaObjectMetadataDescriptor> findByDomain(final List<String> objectIds, final String domain) {
    final MetadataDomainFilter predicate = MetadataDomainFilter.createForKeywords(domain);
    return lookupMultimediaMetadata(objectIds).stream().filter(predicate).collect(Collectors.toList());
  }
  
  
  public List<MediaObjectMetadataDescriptor> lookupMultimediaMetadata(String objectId) {
    final List<MediaObjectMetadataDescriptor> descriptors = reader
        .lookupMultimediaMetadata(objectId);
    if (autoclose) {
      reader.close();
    }
    return descriptors;
  }
  
  public List<MediaObjectMetadataDescriptor> lookupMultimediaMetadata(List<String> ids) {
    final List<MediaObjectMetadataDescriptor> descriptors = reader
        .lookupMultimediaMetadata(ids);
    if (autoclose) {
      reader.close();
    }
    return descriptors;
  }
  
  
  
  private static Predicate<MediaObjectMetadataDescriptor> createDomainAndKeyFilter(final String domain,
                                                                                   final String key) {
    return (m) -> m.getKey().equalsIgnoreCase(key) && m.getDomain().equalsIgnoreCase(domain);
  }
  
}
