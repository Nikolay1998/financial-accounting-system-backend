package kraynov.n.financialaccountingsystembackend.service;

import kraynov.n.financialaccountingsystembackend.dao.NodeDao;
import kraynov.n.financialaccountingsystembackend.dto.NodeDto;
import kraynov.n.financialaccountingsystembackend.dto.TransactionDto;
import kraynov.n.financialaccountingsystembackend.exception.InsufficientFundsException;
import kraynov.n.financialaccountingsystembackend.security.ContextHolderFacade;
import kraynov.n.financialaccountingsystembackend.service.impl.NodeSimpleService;
import kraynov.n.financialaccountingsystembackend.utils.NodeMockDao;
import kraynov.n.financialaccountingsystembackend.utils.TestHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

public class NodeServiceTest {
    private final ContextHolderFacade contextHolderFacade = Mockito.mock(ContextHolderFacade.class);

    private final NodeDao nodeDao = new NodeMockDao();

    private final NodeService nodeService = new NodeSimpleService(nodeDao, contextHolderFacade);

    private final TestHelper testHelper = new TestHelper();

    private NodeDto firstNode;
    private NodeDto secondNode;


    @Before
    public void setUp() {
        when(contextHolderFacade.getAuthenticatedUserOrThrowException())
                .thenReturn(testHelper.getTestUser());
        when(contextHolderFacade.getAuthenticatedUser())
                .thenReturn(testHelper.getTestUser());

        firstNode = testHelper.createNode(new BigDecimal(100), false);
        secondNode = testHelper.createNode(new BigDecimal(100), false);
        nodeDao.save(firstNode);
        nodeDao.save(secondNode);
    }

    @Test
    public void calculateTransactionAffectionTest() {

        TransactionDto transaction = testHelper
                .createTransactionDto(firstNode.getId(), secondNode.getId(), new BigDecimal(100));
        nodeService.calculateTransactionAffection(transaction);

        NodeDto senderNode = nodeDao.getById(this.firstNode.getId());
        NodeDto receiverNode = nodeDao.getById(this.secondNode.getId());
        Assert.assertEquals(new BigDecimal(0), senderNode.getAmount());
        Assert.assertEquals(new BigDecimal(200), receiverNode.getAmount());
    }

    @Test(expected = InsufficientFundsException.class)
    public void calculateTransactionAffectionInsufficientFundsTest() {
        TransactionDto transaction = testHelper
                .createTransactionDto(firstNode.getId(), secondNode.getId(), new BigDecimal(101));

        nodeService.calculateTransactionAffection(transaction);
    }

    @Test
    public void cancelTransactionAffectionTest() {
        TransactionDto transaction = testHelper
                .createTransactionDto(firstNode.getId(), secondNode.getId(), new BigDecimal(100));

        nodeService.calculateTransactionAffection(transaction);

        NodeDto senderNode = nodeDao.getById(this.firstNode.getId());
        NodeDto receiverNode = nodeDao.getById(this.secondNode.getId());
        Assert.assertEquals(new BigDecimal(0), senderNode.getAmount());
        Assert.assertEquals(new BigDecimal(200), receiverNode.getAmount());

        nodeService.cancelTransactionAffection(transaction);

        senderNode = nodeDao.getById(this.firstNode.getId());
        receiverNode = nodeDao.getById(this.secondNode.getId());
        Assert.assertEquals(new BigDecimal(100), senderNode.getAmount());
        Assert.assertEquals(new BigDecimal(100), receiverNode.getAmount());

    }
}