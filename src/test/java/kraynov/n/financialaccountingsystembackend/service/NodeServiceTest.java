package kraynov.n.financialaccountingsystembackend.service;

import kraynov.n.financialaccountingsystembackend.dao.NodeDAO;
import kraynov.n.financialaccountingsystembackend.exception.InsufficientFundsException;
import kraynov.n.financialaccountingsystembackend.model.Node;
import kraynov.n.financialaccountingsystembackend.model.Transaction;
import kraynov.n.financialaccountingsystembackend.security.ContextHolderFacade;
import kraynov.n.financialaccountingsystembackend.service.impl.NodeSimpleService;
import kraynov.n.financialaccountingsystembackend.utils.NodeMockDAO;
import kraynov.n.financialaccountingsystembackend.utils.TestHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

public class NodeServiceTest {
    private final ContextHolderFacade contextHolderFacade = Mockito.mock(ContextHolderFacade.class);

    private final NodeDAO nodeDAO = new NodeMockDAO();

    private final NodeService nodeService = new NodeSimpleService(nodeDAO, contextHolderFacade);

    private final TestHelper testHelper = new TestHelper();

    private Node firstNode;
    private Node secondNode;


    @Before
    public void setUp() {
        when(contextHolderFacade.getAuthenticatedUserOrThrowException())
                .thenReturn(testHelper.getTestUser());
        when(contextHolderFacade.getAuthenticatedUser())
                .thenReturn(testHelper.getTestUser());

        firstNode = testHelper.createNode(new BigDecimal(100), false);
        secondNode = testHelper.createNode(new BigDecimal(100), false);
        nodeDAO.save(firstNode);
        nodeDAO.save(secondNode);
    }

    @Test
    public void calculateTransactionAffectionTest() throws InsufficientFundsException {

        Transaction transaction = testHelper.createTransaction(firstNode.getId(), secondNode.getId(), new BigDecimal(100));
        nodeService.calculateTransactionAffection(transaction);

        Node senderNode = nodeDAO.getById(this.firstNode.getId());
        Node receiverNode = nodeDAO.getById(this.secondNode.getId());
        Assert.assertEquals(new BigDecimal(0), senderNode.getAmount());
        Assert.assertEquals(new BigDecimal(200), receiverNode.getAmount());
    }

    @Test(expected = InsufficientFundsException.class)
    public void calculateTransactionAffectionInsufficientFundsTest() throws InsufficientFundsException {
        Transaction transaction = testHelper.createTransaction(firstNode.getId(), secondNode.getId(), new BigDecimal(101));

        nodeService.calculateTransactionAffection(transaction);
    }

    @Test
    public void cancelTransactionAffectionTest() throws InsufficientFundsException {
        Transaction transaction = testHelper.createTransaction(firstNode.getId(), secondNode.getId(), new BigDecimal(100));

        nodeService.calculateTransactionAffection(transaction);

        Node senderNode = nodeDAO.getById(this.firstNode.getId());
        Node receiverNode = nodeDAO.getById(this.secondNode.getId());
        Assert.assertEquals(new BigDecimal(0), senderNode.getAmount());
        Assert.assertEquals(new BigDecimal(200), receiverNode.getAmount());

        nodeService.cancelTransactionAffection(transaction);

        senderNode = nodeDAO.getById(this.firstNode.getId());
        receiverNode = nodeDAO.getById(this.secondNode.getId());
        Assert.assertEquals(new BigDecimal(100), senderNode.getAmount());
        Assert.assertEquals(new BigDecimal(100), receiverNode.getAmount());

    }
}