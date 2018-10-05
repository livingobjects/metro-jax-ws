/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.jaxws943.server.book;

import com.sun.xml.ws.developer.Stateful;
import com.sun.xml.ws.developer.StatefulWebServiceManager;
import com.sun.xml.ws.developer.StatefulWebServiceManager.Callback;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.soap.Addressing;
import java.util.ArrayList;
import java.util.List;

/**
 * A book instance.
 *
 * @since 2.1 EA2
 */
@Stateful
@WebService(portName = "BookPort", targetNamespace = "http://client.jaxws943.fromjava/book") // target namespace is used for target java package
@Addressing
/**
 * Class to be used for testing stateful WS; Class represents a book with unique id and reviews submitted by users.
 */
public class Book {
    /**
     * The ID of the book.
     * In this example, it's used just to show that we are indeed
     * maintaining multiple instances and requests are dispatched
     * to the right instance.
     */
    private final String id;

    public static final Callback<Book> cbh = (Callback<Book>) new BookCallback();

    /**
     * This object is injected by the JAX-WS RI, and exposes various
     * operations to support stateful web services. Consult its javadoc
     * for more information.
     */
    public static StatefulWebServiceManager<Book> manager;
    public static volatile boolean timeoutSet;


    /**
     * Reviews of this book.
     *
     * <p>
     * The beauty of a stateful web service support is that you can
     * use instance fields like this to store object state.
     * In the real world, you'd probably be persisting this object
     * to database by perhaps using JPA, but this example doesn't show that.
     *
     * <p>
     * To allow concurrenct access, we use a copy-on-write pattern here,
     * hence 'volatile'.
     */
    private volatile List<String> reviews = new ArrayList<String>();

    // unlike stateless web service, the JAX-WS RI will never
    // create an instance. So you can define a constructor with arguments,
    // and in fact you'd normally want to do that so that you can initialize
    // the object with proper state.
    public Book(String id) {
        if (!timeoutSet) {
            manager.setTimeout(5000, cbh);
            timeoutSet = true;
        }
        this.id = id;
    }

    public String getId() {
        return id;
    }

    /**
     * Posts a review.
     *
     * <p>
     * Synchronized to handle concurrent submissions.
     */
    public synchronized void postReview(String review) {
        List<String> l = new ArrayList<String>(reviews);
        l.add(review);
        reviews = l;
    }

    /**
     * Gets all the reviews posted so far.
     */
    public List<String> getReviews() {
        return reviews;
    }

    /**
     * The equals implementation so that two {@link Book}s with the same ID
     * won't be exported twice.
     */
    @WebMethod(exclude = true)
    public boolean equals(Object that) {
        if (that == null || this.getClass() != that.getClass()) return false;
        return this.id.equals(((Book) that).id);
    }

    /**
     * Hashcode implementation consistent with {@link #equals(Object)}.
     */

    @WebMethod(exclude = true)
    public int hashCode() {
        return id.hashCode();
    }

}
