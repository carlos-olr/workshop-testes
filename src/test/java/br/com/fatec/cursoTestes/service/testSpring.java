package br.com.fatec.cursoTestes.service;

import br.com.fatec.cursoTestes.config.TestAppWebConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author carlos.oliveira
 * @since 2016-10-31
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestAppWebConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class testSpring {

    @Autowired
    private Servico service;

    @Test
    public void blah() {
        Assert.assertNotNull(this.service);
    }

}
