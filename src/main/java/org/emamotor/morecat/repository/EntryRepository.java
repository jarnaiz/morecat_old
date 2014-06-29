package org.emamotor.morecat.repository;

import org.emamotor.morecat.model.Entry;
import org.emamotor.morecat.model.EntryState;
import org.emamotor.morecat.model.Entry_;
import org.emamotor.morecat.model.User;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author tanabe
 */
@Stateless
public class EntryRepository extends GenericRepository<Entry> {

  public EntryRepository() {
    super(Entry.class);
  }

  public List<Entry> findAllPublished() {
    setUpCriteria();

    cq.select(entry)
      .where(cb.equal(entry.get(Entry_.state), EntryState.PUBLIC))
      .orderBy(cb.desc(entry.get(Entry_.createdDate)), cb.desc(entry.get(Entry_.createdTime)));

    return getEntityManager().createQuery(cq).getResultList();
  }

  public List<Entry> findAllPublished(int start, int size) {
    setUpCriteria();

    cq.select(entry)
      .where(cb.equal(entry.get(Entry_.state), EntryState.PUBLIC))
      .orderBy(cb.desc(entry.get(Entry_.createdDate)), cb.desc(entry.get(Entry_.createdTime)));

    return getEntityManager().createQuery(cq).setFirstResult(start).setMaxResults(size).getResultList();
  }

  public List<Entry> findAllPublishedByYear(int year) {
    setUpCriteria();

    cq.select(entry)
      .where(
        cb.equal(entry.get(Entry_.state), EntryState.PUBLIC),
        cb.equal(cb.function("year", Integer.class, entry.get(Entry_.createdDate)), year))
      .orderBy(cb.desc(entry.get(Entry_.createdDate)), cb.desc(entry.get(Entry_.createdTime)));

    return getEntityManager().createQuery(cq).getResultList();
  }

  public List<Entry> findAllPublishedByYearMonth(int year, int month) {
    setUpCriteria();

    cq.select(entry)
      .where(
        cb.equal(entry.get(Entry_.state), EntryState.PUBLIC),
        cb.equal(cb.function("year", Integer.class, entry.get(Entry_.createdDate)), year),
        cb.equal(cb.function("month", Integer.class, entry.get(Entry_.createdDate)), month))
      .orderBy(cb.desc(entry.get(Entry_.createdDate)), cb.desc(entry.get(Entry_.createdTime)));

    return getEntityManager().createQuery(cq).getResultList();
  }

  public List<Entry> findAllPublishedByYearMonthDay(int year, int month, int day) {
    setUpCriteria();

    cq.select(entry)
      .where(
        cb.equal(entry.get(Entry_.state), EntryState.PUBLIC),
        cb.equal(cb.function("year", Integer.class, entry.get(Entry_.createdDate)), year),
        cb.equal(cb.function("month", Integer.class, entry.get(Entry_.createdDate)), month),
        cb.equal(cb.function("day", Integer.class, entry.get(Entry_.createdDate)), day))
      .orderBy(cb.desc(entry.get(Entry_.createdDate)), cb.desc(entry.get(Entry_.createdTime)));

    return getEntityManager().createQuery(cq).getResultList();
  }

  public Entry findPublishedByYearMonthDayPermalink(int year, int month, int day, String permalink) {
    setUpCriteria();

    cq.select(entry).where(
      cb.equal(entry.get(Entry_.state), EntryState.PUBLIC),
      cb.equal(cb.function("year", Integer.class, entry.get(Entry_.createdDate)), year),
      cb.equal(cb.function("month", Integer.class, entry.get(Entry_.createdDate)), month),
      cb.equal(cb.function("day", Integer.class, entry.get(Entry_.createdDate)), day),
      cb.equal(entry.get(Entry_.permalink), permalink));

    Entry anEntry;
    try {
      anEntry = getEntityManager().createQuery(cq).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
    return anEntry;
  }

  public List<Entry> findAllByAdmin() {
    setUpCriteria();

    cq.select(entry)
      .orderBy(cb.desc(entry.get(Entry_.createdDate)), cb.desc(entry.get(Entry_.createdTime)));

    return getEntityManager().createQuery(cq).getResultList();
  }

  public List<Entry> findAllByAuthor(User author) {
    setUpCriteria();

    cq.select(entry)
      .where(cb.equal(entry.get(Entry_.author), author))
      .orderBy(cb.desc(entry.get(Entry_.createdDate)), cb.desc(entry.get(Entry_.createdTime)));;

    return getEntityManager().createQuery(cq).getResultList();
  }

  public Set<String> findAllTags() {
    List<Entry> publishedEntries = findAllPublished();
    Set<String> tags = new TreeSet<>();
    for (Entry publishedEntry : publishedEntries) {
      tags.addAll(publishedEntry.getTags());
    }
    return tags;
  }

  public List<Entry> findAllPublishedByTag(String tag) {
    setUpCriteria();

    cq.select(entry)
      .where(
        cb.equal(entry.get(Entry_.state), EntryState.PUBLIC),
        cb.isMember(tag, entry.get(Entry_.tags)))
      .orderBy(cb.desc(entry.get(Entry_.createdDate)), cb.desc(entry.get(Entry_.createdTime)));

    return getEntityManager().createQuery(cq).getResultList();
  }

}
