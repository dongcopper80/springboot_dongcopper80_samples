/*
 * The MIT License
 *
 * Copyright 2022 Nguyễn Thúc Đồng (dongcopper80).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.dongcopper80.olingo.odata.service;

import com.dongcopper80.olingo.odata.config.EntityManagerFilter;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import org.apache.olingo.odata2.api.ODataService;
import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.api.processor.ODataSingleProcessor;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.factory.ODataJPAAccessFactory;
import org.apache.olingo.odata2.jpa.processor.api.factory.ODataJPAFactory;

/**
 *
 * @author Nguyễn Thúc Đồng (dongcopper80)
 */
public class CustomODataServiceFactory extends ODataServiceFactory {
    
    private ODataJPAContext oDataJPAContext;
    
    private ODataContext oDataContext;
    
    @Override
    public final ODataService createService(final ODataContext context) throws ODataException {
        
        oDataContext = context;
        oDataJPAContext = initializeODataJPAContext();

        validatePreConditions();

        ODataJPAFactory factory = ODataJPAFactory.createFactory();
        ODataJPAAccessFactory accessFactory = factory.getODataJPAAccessFactory();

        if (oDataJPAContext.getODataContext() == null) {
            oDataJPAContext.setODataContext(context);
        }

        ODataSingleProcessor oDataSingleProcessor = new CustomODataJpaProcessor(
                oDataJPAContext
        );


        EdmProvider edmProvider = accessFactory.createJPAEdmProvider(oDataJPAContext);
        
        return createODataSingleProcessorService(edmProvider, oDataSingleProcessor);
    }
    
    private void validatePreConditions() throws ODataJPARuntimeException {
        if (oDataJPAContext.getEntityManager() == null) {
            throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.ENTITY_MANAGER_NOT_INITIALIZED, null);
        }
    }
    
    public final ODataJPAContext getODataJPAContext() throws ODataJPARuntimeException {
        if (oDataJPAContext == null) {
            oDataJPAContext = ODataJPAFactory.createFactory().getODataJPAAccessFactory().createODataJPAContext();
        }
        if (oDataContext != null)
            oDataJPAContext.setODataContext(oDataContext);
        return oDataJPAContext;
    }

    protected ODataJPAContext initializeODataJPAContext() throws ODataJPARuntimeException {
        
        ODataJPAContext oDataJPAContext = this.getODataJPAContext();
        ODataContext oDataContext = oDataJPAContext.getODataContext();

        HttpServletRequest request = (HttpServletRequest) oDataContext.getParameter(ODataContext.HTTP_SERVLET_REQUEST_OBJECT);
        EntityManager entityManager = (EntityManager) request.getAttribute(EntityManagerFilter.EM_REQUEST_ATTRIBUTE);
        oDataJPAContext.setEntityManager(entityManager);

        oDataJPAContext.setPersistenceUnitName("default");
        oDataJPAContext.setContainerManaged(true);
        return oDataJPAContext;
    }
}
