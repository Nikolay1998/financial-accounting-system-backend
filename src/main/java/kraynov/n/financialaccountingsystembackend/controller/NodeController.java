package kraynov.n.financialaccountingsystembackend.controller;

import kraynov.n.financialaccountingsystembackend.dao.impl.NodePostgresDAO;
import kraynov.n.financialaccountingsystembackend.model.Node;
import kraynov.n.financialaccountingsystembackend.model.impl.SimpleNodeImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/node")
public class NodeController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private final NodePostgresDAO nodePostgresDAO;

    public NodeController(NodePostgresDAO nodePostgresDAO) {
        this.nodePostgresDAO = nodePostgresDAO;
    }

    @GetMapping(path = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Node> getAll() {
        return nodePostgresDAO.getAll();
    }

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Node add(@RequestBody SimpleNodeImpl node) {
        return nodePostgresDAO.save(node);
    }

}
