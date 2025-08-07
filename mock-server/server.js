// mock-server/server.js

const jsonServer = require('json-server');
const path = require('path');
const server = jsonServer.create();
const router = jsonServer.router(path.join(__dirname, 'db.json'));
const middlewares = jsonServer.defaults();

server.use(middlewares);
server.use(jsonServer.bodyParser);

// 1) Autenticação: sempre retorna 200 OK
server.post('/auth/login', (req, res) => {
    res.status(200).jsonp({
        token: 'fake-jwt-token',
        refreshToken: 'fake-refresh-token',
        user: { id: 1, username: req.body.username || 'user' },
    });
});

// 2) Produtos autenticados
server.get('/auth/products', (req, res) => {
    const auth = req.headers.authorization || '';
    if (!auth.startsWith('Bearer ')) {
        return res.status(403).jsonp({ message: 'Authentication Problem' });
    }
    if (auth !== 'Bearer fake-jwt-token') {
        return res.status(401).jsonp({ message: 'Invalid/Expired Token!' });
    }
    const products = router.db.get('products').value();
    return res.status(200).jsonp(products);
});

// 3) Listagem pública de produtos
server.get('/products', (req, res) => {
    const products = router.db.get('products').value();
    return res.status(200).jsonp(products);
});

// 4) Validação de payload de produto (name + price)
const productValidator = (req, res, next) => {
    const { name, price } = req.body;
    // name deve ser string não vazia, price número > 0
    if (typeof name !== 'string' || name.trim() === '' || typeof price !== 'number' || price <= 0) {
        return res
            .status(400)
            .jsonp({ error: 'name and price are required' });
    }
    next();
};

// 5) Rota custom para /products/add
server.post('/products/add', productValidator);
server.post('/products/add', (req, res) => {
    const db = router.db;
    const list = db.get('products');
    const newId = list.map('id').max().value() + 1;
    const product = { id: newId, name: req.body.name.trim(), price: req.body.price };
    list.push(product).write();
    return res.status(201).jsonp(product);
});

// 6) Também intercepta POST /products (JSON-Server padrão) para validar
server.post('/products', productValidator);
server.post('/products', (req, res) => {
    router.handle(req, res);
});

// 7) Demais rotas padrão
server.use(router);

// Inicia o servidor
const PORT = 3000;
server.listen(PORT, () => {
    console.log(`🚀 Mock API server running on http://localhost:${PORT}`);
});
