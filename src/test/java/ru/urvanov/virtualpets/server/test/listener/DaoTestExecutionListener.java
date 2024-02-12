/**
 * 
 */
package ru.urvanov.virtualpets.server.test.listener;

import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.util.fileloader.XlsDataFileLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

import ru.urvanov.virtualpets.server.test.annotation.DataSets;



/**
 * @author fedya
 *
 */
public class DaoTestExecutionListener implements TestExecutionListener {

	private Logger logger = LoggerFactory.getLogger(DaoTestExecutionListener.class);
    /**
     * 
     */
    public DaoTestExecutionListener() {
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see org.springframework.test.context.TestExecutionListener#afterTestClass(org.springframework.test.context.TestContext)
     */
    @Override
    public void afterTestClass(TestContext arg0) throws Exception {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see org.springframework.test.context.TestExecutionListener#afterTestMethod(org.springframework.test.context.TestContext)
     */
    @Override
    public void afterTestMethod(TestContext arg0) throws Exception {
        //if (databaseTester != null) {
        //	databaseTester.getConnection().getConfig().setProperty(DatabaseConfig.PROPERTY_ESCAPE_PATTERN, "`?`");
        //	databaseTester.setTearDownOperation(DatabaseOperation.TRUNCATE_TABLE);
         //  databaseTester.onTearDown();
        //}
    }

    /* (non-Javadoc)
     * @see org.springframework.test.context.TestExecutionListener#beforeTestClass(org.springframework.test.context.TestContext)
     */
    @Override
    public void beforeTestClass(TestContext arg0) throws Exception {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see org.springframework.test.context.TestExecutionListener#beforeTestMethod(org.springframework.test.context.TestContext)
     */
    @Override
    public void beforeTestMethod(TestContext testCtx) throws Exception {
        DataSets dataSetAnnotation = testCtx.getTestMethod().getAnnotation(DataSets.class);
        if (dataSetAnnotation == null ) {
            return;
        }
        //UserService userService = (UserService)testCtx.getApplicationContext().getBean("userService");
        
        String dataSetName = dataSetAnnotation.setUpDataSet();
        
        if (!dataSetName.equals("")) {
            IDatabaseTester databaseTester = (IDatabaseTester)
                    testCtx.getApplicationContext().getBean("databaseTester");
            XlsDataFileLoader xlsDataFileLoader = (XlsDataFileLoader)
                    testCtx.getApplicationContext().getBean("xlsDataFileLoader");
            IDataSet dataSet = xlsDataFileLoader.load(dataSetName);
            
            databaseTester.setDataSet(dataSet);
            databaseTester.onSetup();
        }
    }

    /* (non-Javadoc)
     * @see org.springframework.test.context.TestExecutionListener#prepareTestInstance(org.springframework.test.context.TestContext)
     */
    @Override
    public void prepareTestInstance(TestContext arg0) throws Exception {
        // TODO Auto-generated method stub

    }

}
