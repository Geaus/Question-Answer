describe('template spec', () => {
  it('测试未登录时的跳转', () => {
    cy.visit('http://localhost:3000')
    cy.url().should("include", "/login")
  })
})